package com.netshelter.ifbrands.etl.transform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.netshelter.ifbrands.api.model.entity.Author;
import com.netshelter.ifbrands.etl.dataplatform.model.DpAuthor;
import com.netshelter.ifbrands.etl.dataplatform.model.GetAuthorsResponse;

@Component
public class AuthorTransformer
{
    public List<Author> transform(GetAuthorsResponse response)
    {
        List<Author> result = new ArrayList<Author>(response.getResultCount());
        for (DpAuthor item : response.getAuthors())
        {
            Author a = new Author(item.getId());
            a.setName(item.getName());
            result.add(a);
        }
        return result;
    }

    public Map<Integer, Author> transformMap(GetAuthorsResponse response)
    {
        Map<Integer, Author> result = new HashMap<Integer, Author>();
        for (DpAuthor item : response.getAuthors())
        {
            Author a = new Author(item.getId());
            a.setName(item.getName());
            result.put(a.getId(), a);
        }
        return result;
    }
}
