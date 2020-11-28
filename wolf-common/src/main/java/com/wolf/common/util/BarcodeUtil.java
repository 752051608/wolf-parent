package com.wolf.common.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
import org.springframework.util.StringUtils;

import java.awt.image.BufferedImage;
import java.io.*;

public class BarcodeUtil {
    /**
     * 生成文件
     *
     * @param msg
     * @param path
     * @return
     */
    public static File generateFile(String msg, String path) {
        File file = new File(path);
        try {
            generate(msg, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    /**
     * 生成字节
     *
     * @param msg
     * @return
     */
    public static byte[] generate(String msg) {
        ByteArrayOutputStream ous = new ByteArrayOutputStream();
        generate(msg, ous);
        return ous.toByteArray();
    }

    /**
     * 生成到流
     *
     * @param msg
     * @param ous
     */
    public static void generate(String msg, OutputStream ous) {
        if (StringUtils.isEmpty(msg) || ous == null) {
            return;
        }

        Code39Bean bean = new Code39Bean();

        // 精细度
        final int dpi = 150;
        // module宽度
        final double moduleWidth = UnitConv.in2mm(2f / dpi);

        // 配置对象
        bean.setModuleWidth(moduleWidth);
        bean.setWideFactor(4);
        bean.doQuietZone(true);
        //设置不显示字符,字符显示在图片太挤,bug现象譬如3os7wjwuiy的j展示时挤压的同i一样。纯数字无影响
        bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
        String format = "image/png";
        try {
            // 输出到流
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi,
                    BufferedImage.TYPE_BYTE_BINARY, false, 0);
            // 生成条形码
            bean.generateBarcode(canvas, msg);
            // 结束绘制
            canvas.finish();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static boolean uploadFile(String host, int port, String username, String password, String basePath,
                                     String filePath, String filename, InputStream input) {        boolean result = false;
        FTPClient ftp = new FTPClient();        try {            int reply;
           ftp.connect(host, port);// 连接FTP服务器
//            ftp.connect(host);// 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();                return result;
            }            //切换到上传目录
            if (!ftp.changeWorkingDirectory(basePath+filePath)) {                //如果目录不存在创建目录
                String[] dirs = filePath.split("/");
                String tempPath = basePath;                for (String dir : dirs) {                    if (null == dir || "".equals(dir)) continue;
                    tempPath += "/" + dir;                    if (!ftp.changeWorkingDirectory(tempPath)) {                        if (!ftp.makeDirectory(tempPath)) {                            return result;
                    } else {
                        ftp.changeWorkingDirectory(tempPath);
                    }
                    }
                }
            }            //设置为被动模式
            ftp.enterLocalPassiveMode();            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);            //上传文件
            if (!ftp.storeFile(filename, input)) {                return result;
            }
            input.close();
            ftp.logout();
            result = true;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {            if (ftp.isConnected()) {                try {
            ftp.disconnect();
        } catch (IOException ioe) {
        }
        }
        }        return result;
    }




    public static void main(String[] args) {
        String msg = "8uodn4u76q";
        //  String path = "D:/barcode.png";
        //  File file = generateFile(msg, path);

            InputStream inputStream = null;
            //1、给上传的图片生成新的文件名
            //1.1获取原始文件名
            //1.2使用IDUtils工具类生成新的文件名，新文件名 = newName + 文件后缀
            String newName = msg ;
            String filePath = newName  + ".png";
            String path = "/Users/zhanghong/pic/" + filePath;
           //2、包括图片的url保存到数据库
            generateFile(msg,path);

        }

}
