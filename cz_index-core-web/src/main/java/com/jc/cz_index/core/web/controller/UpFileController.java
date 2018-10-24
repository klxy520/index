package com.jc.cz_index.core.web.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSONObject;
import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.common.utils.ContentUtils;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.SystemUser;
import com.jc.cz_index.model.UpFile;
import com.jc.cz_index.core.service.ISystemConfigService;
import com.jc.cz_index.core.service.IUpFileService;

/**
 * 描述：
 * 
 * @author liujicheng 2016年9月28日 上午10:52:01
 * @version 1.0.1
 * @since 1.0.1
 */
@Controller
@RequestMapping(value = "/upFile")
public class UpFileController {
    private static final Logger  LOG = Logger.getLogger(UpFileController.class);
    @Autowired
    private IUpFileService       upFileService;
    @Autowired
    private ISystemConfigService systemConfigService;



    /**
     * 描述：
     * 
     * @param modul
     * @param fileType
     * @param request
     * @param session
     * @return
     * @author liujicheng 2016年10月13日 下午3:53:11
     * @version 1.0
     */
    @RequestMapping("/save")
    @ResponseBody
    public ResponseData save(@RequestParam(value = "modul", required = true) String modul,
            @RequestParam(value = "fileType", required = true) String fileType, HttpServletRequest request, HttpSession session) {
        List<Map<String, Object>> fileInfos = new ArrayList<Map<String, Object>>();
        // 获取解析器
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        // 判断是否是文件
        if (resolver.isMultipart(request)) {

            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) (request);
            Iterator<String> it = multiRequest.getFileNames();
            List<CommonsMultipartFile> comonList = new ArrayList<CommonsMultipartFile>();
            while (it.hasNext()) {
                // 根据文件名称取文件
                comonList.add((CommonsMultipartFile) multiRequest.getFile(it.next()));
            }
            CommonsMultipartFile[] files = null;
            if (comonList.size() > 0) {
                files = (CommonsMultipartFile[]) comonList.toArray(new CommonsMultipartFile[comonList.size()]);
            }

            if (files != null) {
                String systemConfigKey = "file_upload_" + modul + "_" + fileType;
                String configPath = systemConfigService.getValue(systemConfigKey);// 对应功能块存储文件相对路径
                String realFolderPath = getSysConfigFileRootPath() + configPath;// 包括根目录的全路径
                for (int i = 0; i < files.length; i++) {

                    if (!files[i].isEmpty()) {

                        if (StringUtils.isEmpty(configPath)) {
                            LOG.error("上传文件传入参数错误!");
                            return ResponseData.getErrorResponse("上传文件传入参数错误!");
                        }

                        // 获取文件名中的后缀
                        String fileName = files[i].getOriginalFilename();
                        String Suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());

                        String newFileName = modul + "_" + fileType + "_" + new Date().getTime() + Suffix;
                        String relativeFilePath = configPath + "/" + newFileName;

                        // 文件夹是否存在，不存在则创建该文件夹
                        File folderFile = new File(realFolderPath);
                        if (!(folderFile.exists() && folderFile.isDirectory())) {
                            if (folderFile.exists()) {
                                LOG.error("上传文件夹创建失败,存在同名文件!");
                                return ResponseData.getErrorResponse("上传文件失败！服务器内部错误！");
                            } else {
                                folderFile.mkdirs();
                            }
                        }

                        try {
                            writeByte(files[i].getBytes(), realFolderPath + "/" + newFileName);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            return ResponseData.getErrorResponse("服务器错误！");
                        }

                        UpFile upFile = new UpFile();
                        upFile.setCreateDate(new Date());
                        upFile.setFileSize(files[i].getBytes().length / 1024);// 文件大小以Kb为单位
                        upFile.setState(1);
                        upFile.setNewFileName(newFileName);
                        upFile.setFilePath(relativeFilePath);
                        upFile = upFileService.doAddModel(upFile);

                        Map<String, Object> fileInfo = new HashMap<String, Object>();
                        fileInfo.put("fileId", upFile.getId());
                        fileInfo.put("filePath", upFile.getFilePath());
                        fileInfos.add(fileInfo);
                    }
                }
            } else {
                return ResponseData.getErrorResponse("没有获取到文件！");
            }

        }
        return ResponseData.getSuccessResponse(fileInfos);
    }



    /**
     * 保存excel：
     * 
     * @param fileType
     * @param request
     * @param session
     * @return
     * @author liujicheng 2016年10月13日 下午3:53:11
     * @version 1.0
     */
    @RequestMapping("/saveExcel")
    @ResponseBody
    public String saveExcel(HttpServletRequest request, HttpSession session) {
        List<Map<String, Object>> fileInfos = new ArrayList<Map<String, Object>>();
        // 获取解析器
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        // 判断是否是文件
        if (resolver.isMultipart(request)) {

            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) (request);
            Iterator<String> it = multiRequest.getFileNames();
            List<CommonsMultipartFile> comonList = new ArrayList<CommonsMultipartFile>();
            while (it.hasNext()) {
                // 根据文件名称取文件
                comonList.add((CommonsMultipartFile) multiRequest.getFile(it.next()));
            }
            CommonsMultipartFile[] files = null;
            if (comonList.size() > 0) {
                files = (CommonsMultipartFile[]) comonList.toArray(new CommonsMultipartFile[comonList.size()]);
            }

            if (files != null) {
                String systemConfigKey = "file_upload_excel_cust_info_temp";
                String configPath = systemConfigService.getValue(systemConfigKey);// 对应功能块存储文件相对路径
                String realFolderPath = getSysConfigFileRootPath() + configPath;// 包括根目录的全路径
                for (int i = 0; i < files.length; i++) {

                    if (!files[i].isEmpty()) {

                        if (StringUtils.isEmpty(configPath)) {
                            LOG.error("上传文件传入参数错误!");
                            return JSONObject.toJSONString(ResponseData.getErrorResponse("上传文件传入参数错误!"));
                        }

                        // 获取文件名中的后缀
                        String fileName = files[i].getOriginalFilename();
                        String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());

                        // 获取当前用户
                        SystemUser user = (SystemUser) WebUtils.getLoginUser();
                        if(user==null){
                            return JSONObject.toJSONString(ResponseData.getErrorResponse("用户登录失效",-1));
                        }
                        String newFileName = "custInfoTemp_" + user.getId() + suffix;
                        String relativeFilePath = configPath + "/" + newFileName;

                        // 文件夹是否存在，不存在则创建该文件夹
                        File folderFile = new File(realFolderPath);
                        if (!(folderFile.exists() && folderFile.isDirectory())) {
                            if (folderFile.exists()) {
                                LOG.error("上传文件夹创建失败,存在同名文件!");
                                return JSONObject.toJSONString(ResponseData.getErrorResponse("上传文件失败！服务器内部错误！"));
                            } else {
                                folderFile.mkdirs();
                            }
                        }

                        try {
                            writeByte(files[i].getBytes(), realFolderPath + "/" + newFileName);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            return JSONObject.toJSONString(ResponseData.getErrorResponse("服务器错误！"));
                        }

                        QueryParams params = new QueryParams();
                        params.addParameter("new_file_name", newFileName);
                        UpFile upFile = upFileService.queryOneDetail(params);
                        if (upFile == null) {
                            upFile = new UpFile();
                        }
                        upFile.setCreateDate(new Date());
                        upFile.setFileSize(files[i].getBytes().length / 1024);// 文件大小以Kb为单位
                        upFile.setState(1);
                        upFile.setNewFileName(newFileName);
                        upFile.setFilePath(relativeFilePath);
                        if (upFile.getId() != null) {
                            upFile = upFileService.doUpdateModel(upFile);
                        } else {
                            upFile = upFileService.doAddModel(upFile);
                        }

                        Map<String, Object> fileInfo = new HashMap<String, Object>();
                        fileInfo.put("fileId", upFile.getId());
                        fileInfo.put("filePath", upFile.getFilePath());
                        fileInfos.add(fileInfo);
                    }
                }
            } else {
                return JSONObject.toJSONString(ResponseData.getErrorResponse("没有获取到文件！"));
            }

        }
        return JSONObject.toJSONString(ResponseData.getSuccessResponse(fileInfos));
    }



    private void writeByte(byte[] bytes, String path) throws IOException {
        FileOutputStream os = null;
        ByteArrayInputStream bin = null;
        try {

            os = new FileOutputStream(path);
            bin = new ByteArrayInputStream(bytes);

            int chipSize = 102400;// 每次读取100kb
            int avai = bin.available();
            int len;
            byte[] readBytes = new byte[chipSize];
            while (avai > 0) {
                len = chipSize > avai ? avai : chipSize;
                bin.read(readBytes, 0, len);
                os.write(readBytes, 0, len);
                avai -= len;
            }

        } catch (IOException e) {
            e.printStackTrace();
            LOG.error("上传出错！");
            throw e;
        } finally {

            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    LOG.error("FileOutputStream close IOException:" + e.getMessage());
                }
            }

            if (bin != null) {
                try {
                    bin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    LOG.error("FileInputStream close IOException:" + e.getMessage());
                }
            }

        }
    }



    /**
     * 描述：根据多个id查询出多个文件信息
     * 
     * @param ids
     * @param request
     * @param session
     * @return
     * @author liujicheng 2016年10月13日 下午8:20:27
     * @version 1.0
     */
    @RequestMapping(value = "/getByIds", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getByIds(@RequestBody List<Long> ids, HttpServletRequest request, HttpSession session) {
        List<UpFile> files = upFileService.queryByIds(ids);
        return ResponseData.getSuccessResponse(files);
    }



    /**
     * 描述：根据单个id查询出文件信息
     * 
     * @param id
     * @param request
     * @param session
     * @return
     * @author liujicheng 2016年10月13日 下午8:20:31
     * @version 1.0
     */
    @RequestMapping("/getById")
    @ResponseBody
    public ResponseData getById(Long id, HttpServletRequest request, HttpSession session) {
        UpFile file = upFileService.getModelById(id);
        return ResponseData.getSuccessResponse(file);
    }



    /**
     * 获取配置库文件根路径
     * 
     * @return
     */
    private String getSysConfigFileRootPath() {
        String rootPath = systemConfigService.getValue(ContentUtils.FILE_UPLOAD_ROOT_PATH);
        // 如果不为空并且最后一位为/
        if (StringUtils.isNotEmpty(rootPath) && rootPath.substring(rootPath.length() - 1).equals("/")) {
        } else {
            if (StringUtils.isEmpty(rootPath)) {
                rootPath = "";
            }
            rootPath = rootPath + "/";
        }
        return rootPath;
    }
}
