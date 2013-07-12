package com.netshelter.ifbrands.etl.transform;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.netshelter.ifbrands.api.model.entity.Brand;
import com.netshelter.ifbrands.etl.dataplatform.model.DpCategory;
import com.netshelter.ifbrands.etl.dataplatform.model.GetCategoriesResponse;

@Component
public class BrandTransformer
{
  public List<Brand> transform( GetCategoriesResponse response )
  {
    List<Brand> result = new ArrayList<Brand>( response.getResultCount() );
    for( DpCategory item: response.getCategories() ) {
      Brand b = new Brand( item.getId() );
      b.setName( item.getName() );
      result.add( b );
    }
    return result;
  }
}
