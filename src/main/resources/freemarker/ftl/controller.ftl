/**
 * @filename:${entityName}Controller ${createTime}
 * @project ${project}  ${version}
 * Copyright(c) 2020 ${author} Co. Ltd.
 * All right reserved.
 */
package ${controllerUrl};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import ${entityUrl}.${entityName};
import ${serviceUrl}.${entityName}Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
<#if isSwagger=="true" >
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
</#if>
/**
 * <p>自动生成工具：mybatis-dsc-generator</p>
 *
 * <p>说明： ${entityComment}API接口层</P>
 * @version: ${version}
 * @author: ${author}
 * @time    ${createTime}
 *
 */
<#if isSwagger=="true" >
@Api(tags = "${entityComment}")
</#if>
@RestController
@RequestMapping("/${objectName}")
public class ${entityName}Controller {

      @Autowired
      protected ${entityName}Service baseService;

      /**
      * @explain 查询对象  <swagger GET请求>
       * @param   对象参数：id
       * @return  CommonResult
       * @author  ${author}
       * @time    ${createTime}
       */
       @GetMapping("/getById/{id}")
       <#if isSwagger=="true" >
       @ApiOperation(value = "获取对象", notes = "作者：${author}")
       @ApiImplicitParam(paramType="path", name = "id", value = "对象id", required = true, dataType = "Long")
       </#if>
       public CommonResult getById(@PathVariable("id")Long id){
            ${entityName} obj=baseService.getById(id);
            if (null!=obj ) {
                CommonResult.success(obj);
            }else {
                CommonResult.failed("查询对象不存在！");
            }
            return CommonResult.failed();
        }

        /**
        * @explain 删除对象
        * @param   对象参数：id
        * @return  CommonResult
        * @author  ${author}
        * @time    ${createTime}
        */
        @PostMapping("/deleteById")
        <#if isSwagger=="true" >
        @ApiOperation(value = "删除", notes = "作者：${author}")
        @ApiImplicitParam(paramType="query", name = "id", value = "对象id", required = true, dataType = "Long")
        </#if>
        public CommonResult deleteById(Long id){
            ${entityName} obj=baseService.getById(id);
            if (null!=obj) {
                boolean rsg = baseService.removeById(id);
            if (rsg) {
                CommonResult.success("删除成功");
            }else {
                CommonResult.failed("删除失败！");
            }
            }else {
                CommonResult.failed("删除的对象不存在！");
            }
            return CommonResult.failed();
        }



       /**
       * @explain 添加
       * @param   对象参数：${entityName}
       * @return  Boolean
       * @author  ${author}
       * @time    ${createTime}
       */
       @PostMapping("/insert")
       <#if isSwagger=="true" >
       @ApiOperation(value = "添加", notes = "作者：${author}")
       </#if>
       public CommonResult insert(${entityName} entity){
           if (null!=entity) {
                boolean rsg = baseService.save(entity);
           if (rsg) {
                CommonResult.success("添加成功");
           }else {
                CommonResult.failed("添加失败！");
           }
           }else {
                CommonResult.failed("请传入正确参数！");
           }
           return CommonResult.failed();
      }



      /**
      * @explain 修改
      * @param   对象参数：T
      * @return  Boolean
      * @author  ${author}
      * @time    ${createTime}
      */
      @PostMapping("/update")
      <#if isSwagger=="true" >
      @ApiOperation(value = "修改", notes = "作者：${author}")
      </#if>
      public CommonResult update(${entityName} entity){
          if (null!=entity) {
                boolean rsg = baseService.updateById(entity);
          if (rsg) {
                CommonResult.success("修改成功");
          }else {
                CommonResult.failed("修改失败！");
          }
          }else {
                CommonResult.failed("请传入正确参数！");
          }
          return CommonResult.failed();
      }


       /**
       * @explain 分页条件查询
       * @param   对象参数：AppPage<${entityName}>
       * @return  PageInfo<${entityName}>
       * @author  ${author}
       * @time    ${createTime}
       */
       @GetMapping("/getPages")
       <#if isSwagger=="true" >
       @ApiOperation(value = "分页查询", notes = "分页查询返回[IPage<${entityName}>],作者：${author}")
       </#if>
       public CommonResult<IPage<${entityName}>> getPages(PageParam<${entityName}> param,${entityName} entity){
           Page<${entityName}> page=new Page<${entityName}>(param.getPageNum(),param.getPageSize());
           QueryWrapper<${entityName}> queryWrapper =new QueryWrapper<${entityName}>();
           queryWrapper.setEntity(entity);
           //分页数据
           IPage<${entityName}> pageData=baseService.page(page, queryWrapper);

           return CommonResult.success(pageData);
       }
}