package com.netshelter.ifbrands.etl.transform;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.netshelter.ifbrands.api.model.entity.Site;
import com.netshelter.ifbrands.etl.dataplatform.model.DpSite;
import com.netshelter.ifbrands.etl.dataplatform.model.GetSitesResponse;

@Component
public class SiteTransformer
{
  public List<Site> transform( GetSitesResponse response )
  {
    List<Site> result = new ArrayList<Site>( response.getResultCount() );
    for( DpSite item: response.getSites() ) {
      Site s = new Site( item.getId() );
      s.setName( item.getName() );
      s.setDomain( item.getDomain() );
      s.setNetwork( item.getNetwork().getName() );
      result.add( s );
    }
    return result;
  }
}
