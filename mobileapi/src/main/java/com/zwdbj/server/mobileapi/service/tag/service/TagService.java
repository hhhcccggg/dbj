package com.zwdbj.server.mobileapi.service.tag.service;


import com.zwdbj.server.mobileapi.service.tag.mapper.ITagMapper;
import com.zwdbj.server.mobileapi.service.tag.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TagService {
    @Autowired
    ITagMapper tagMapper;

    public List<TagDto> search(TagSearchInput input) {
        List<TagDto> dtos = this.tagMapper.search(input);
        return dtos;
    }
}
