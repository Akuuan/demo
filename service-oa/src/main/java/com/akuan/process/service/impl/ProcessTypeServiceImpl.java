package com.akuan.process.service.impl;

import com.akuan.process.mapper.ProcessTypeMapper;
import com.akuan.process.service.ProcessTypeService;
import com.atguigu.model.process.ProcessType;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class ProcessTypeServiceImpl extends ServiceImpl<ProcessTypeMapper, ProcessType> implements ProcessTypeService {
}
