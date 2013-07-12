#!/bin/bash
yum -y install tomcat7 
chkconfig tomcat7 on

echo  "Grab stack vars..."
source /etc/netshelter/cfn-vars.sh
echo
echo Set deploy and opscommon dirs
export DEPLOY_DIR="${BOOTSTRAP_DIR}${PROJECT_NAME}/deploy/"
export OPSCOMMON_DIR="${BOOTSTRAP_DIR}${PROJECT_NAME}/deploy/opscommon/"
echo
echo "Standard config..."
# STANDARD config: installs all NetShelter Standard variables
STDCONF="${BOOTSTRAP_DIR}${PROJECT_NAME}/deploy/opscommon/envconfig/std_envconf.sh"
source ${STDCONF}
# copy to a well known location for other scripts.
cp ${STDCONF} /etc/netshelter/std-vars.sh
echo
echo "Standard app config..."
# STANDARD config: installs all NetShelter Standard applications
STDAPPCONF="${BOOTSTRAP_DIR}${PROJECT_NAME}/deploy/opscommon/scripts/std_appconf.sh"
source ${STDAPPCONF}
# copy to a well known location for other scripts.
cp ${STDAPPCONF} /etc/netshelter/std_appconf.sh	

# installs common variables used throughout stack but not defined in json
source ${BOOTSTRAP_DIR}${PROJECT_NAME}/deploy/opscommon/envconfig/${ENVIRONMENT}_envconf.sh

# gets environment specific variables for IFB applications
ENVAPPCONF="${BOOTSTRAP_DIR}${PROJECT_NAME}/deploy/ifbdeploycommon/conf/${ENVIRONMENT}.sh"
source ${ENVAPPCONF}
cp ${ENVAPPCONF} /etc/netshelter/${ENVIRONMENT}.sh

#values listed here change based on PROJECT_NAME - tested dynamic variable creation - didn't like for reasons of supportability
#IFBBE_DB_HOST ; IFB_DB_PORT ; IFB_DB_DBNAME ; IFB_DB_USER ; IFB_DB_PASS ; IFB_S3_BUCKET_FEEDS ; IFB_STATS_ROOT
case ${PROJECT_NAME} in
	ifbbe) IFBBE_DB_HOST=$IFBBE_DB_PROPER_HOST ; IFB_DB_PORT=$IFB_DB_PROPER_PORT ; IFB_DB_DBNAME=$IFB_DB_PROPER_DBNAME ; IFB_DB_USER=$IFB_DB_PROPER_USER ; IFB_DB_PASS=$IFB_DB_PROPER_PASS ; IFB_S3_BUCKET_FEEDS=$IFB_S3_BUCKET_PROPER_FEEDS ; IFB_STATS_ROOT=$IFB_STATS_PROPER_ROOT ;; 
	ifbbedemo) IFBBE_DB_HOST=$IFBBE_DB_DEMO_HOST ; IFB_DB_PORT=$IFB_DB_DEMO_PORT ; IFB_DB_DBNAME=$IFB_DB_DEMO_DBNAME ; IFB_DB_USER=$IFB_DB_DEMO_USER ; IFB_DB_PASS=$IFB_DB_DEMO_PASS ; IFB_S3_BUCKET_FEEDS=$IFB_S3_BUCKET_DEMO_FEEDS ; IFB_STATS_ROOT=$IFB_STATS_DEMO_ROOT ;;
	#if error occurs, *) catches and sets to "_PROPER_" variables
	*) IFBBE_DB_HOST=$IFBBE_DB_PROPER_HOST ; IFB_DB_PORT=$IFB_DB_PROPER_PORT ; IFB_DB_DBNAME=$IFB_DB_PROPER_DBNAME ; IFB_DB_USER=$IFB_DB_PROPER_USER ; IFB_DB_PASS=$IFB_DB_PROPER_PASS ; IFB_S3_BUCKET_FEEDS=$IFB_S3_BUCKET_PROPER_FEEDS ; IFB_STATS_ROOT=$IFB_STATS_PROPER_ROOT ;;
esac

cat > /usr/share/tomcat7/lib/ifbrands-environment.properties <<EOF
# iFB db Parameters
jdbc.ifbrands.url=jdbc:mysql://$IFBBE_DB_HOST:$IFB_DB_PORT/$IFB_DB_DBNAME
jdbc.ifbrands.username=$IFB_DB_USER
jdbc.ifbrands.password=$IFB_DB_PASS

# dashboard Properties
statsd.host=$STATSD_HOST
statsd.root=$IFB_STATS_ROOT
statsd.port=$STATSD_PORT

# dpservices Properties
dpservices.authority=$DP_SERVICES_HOSTNAME:$DP_SERVICES_ACCESS_PORT

# dpapi Properties
dpinsights.authority=$DPAPI_URL

# user manager Properties
usermanager.authority=$USERMANAGER_API_URL

# shareapi Properties
shareapi.authority=$SHARE_API_HOSTNAME

# Amazon credentials 
netshelter.ifb.aws.accessKey=$IFB_IAM_ACCESS_KEY
netshelter.ifb.aws.secretKey=$IFB_IAM_SECRET_KEY

# s3 buckets
netshelter.ifb.s3.bucket.feeds=$IFB_S3_BUCKET_FEEDS
netshelter.ifb.cdn.endpoint.feeds=$IFB_CDN_ENDPOINT_FEEDS

# NAP Clerk Properties
napclerk.authority=$NAP_CLERK_AUTHORITY
EOF

#install IFB BE application
cp ${BOOTSTRAP_DIR}${PROJECT_NAME}/target/ifbrands.war /var/lib/tomcat7/webapps/

#restart tomcat7
service tomcat7 restart
