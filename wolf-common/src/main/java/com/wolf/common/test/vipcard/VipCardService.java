package com.wolf.common.test.vipcard;


import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.context.AnalysisContext;
import com.alibaba.excel.read.event.AnalysisEventListener;
import com.wolf.common.util.ExcelReaderFactory;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;


@Service
public class VipCardService {





    public  void importVipData()  throws Exception{
        try (InputStream inputStream = Files.newInputStream(Paths.get("C:/Users/admin/Desktop/VIP3.xls"));) {
             List<VipCardModel> list = new ArrayList<>();
            AnalysisEventListener<VipCardModel> listener = new AnalysisEventListener<VipCardModel>() {

                @Override
                public void invoke(VipCardModel object, AnalysisContext context) {
                //    System.err.println("Row:" + context.getCurrentRowNum() + " Data:" + object);
                    list.add(object);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext context) {
                    System.err.println("doAfterAllAnalysed...");
                }
            };
            ExcelReader excelReader = ExcelReaderFactory.getExcelReader(inputStream, null, listener);
            // 第二个参数为表头行数，按照实际设置
            excelReader.read(new Sheet(1, 1, VipCardModel.class));

            //插入customer以及customerInfo表
//            for (VipCardModel vipCardModel : list) {
            list.parallelStream().forEach(vipCardModel->{

                String phone = vipCardModel.getPhone();
                String password = vipCardModel.getCardNum();

                if(phone != null){

                    //business
                }


                //business
            });
        }

    }

}
