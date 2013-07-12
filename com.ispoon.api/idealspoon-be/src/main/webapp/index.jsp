<%@ page import="java.util.Date" %>
<%@ page import="net.sf.ehcache.CacheManager" %>
<%@ page import="net.sf.ehcache.Cache" %>
<%@ page import="java.util.List" %>
<%@ page import="org.hibernate.cache.CacheKey" %>
<%@ page import="java.lang.management.ManagementFactory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
//    CacheManager manager = CacheManager.getInstance();
//    String [] caches = manager.getCacheNames();
//    for (int j = 0; j < caches.length; j++)
//    {
//        String cacheName = caches[j];
//        if (cacheName.startsWith("com."))
//        {
//            Cache cache = manager.getCache(cacheName);
//            List keys = cache.getKeys();
//            for (int i = 0; i < keys.size(); i++) {
//                Object o = keys.get(i);
//                System.out.println("cache("+cache.getName()+") key: " + o + "\t\t value: " + o.getClass().getSimpleName());
//            }
//        }
//    }
    long jvmUpTime = ManagementFactory.getRuntimeMXBean().getUptime();
%>
<html>
<head>
    <title>IFBrands Platform</title>
    <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
</head>
<body>
   <div style="text-align: center;width: 100%;">
       <div>
           <br/><br/><br/><br/><br/><br/><br/><br/><br/>
           <div style="font-family: Georgia,Verdana,serif;font-size: 26px">
           <h1>IFBrands Platform</h1>
           </div>
           Server time: <%=new Date()%>
           <br/><br/>
           Up time: <%=jvmUpTime%>
       </div>
   </div>
</body>
</html>