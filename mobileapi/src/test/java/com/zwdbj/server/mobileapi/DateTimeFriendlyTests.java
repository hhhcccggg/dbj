package com.zwdbj.server.mobileapi;

import com.zwdbj.server.utility.common.DateTimeFriendly;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DateTimeFriendlyTests {
    @Test
    public void showTimeFriendly() throws Exception {
        SimpleDateFormat simFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        Date dtBeg = simFormat.parse("2018.08.15 09:45:56");
        String v = DateTimeFriendly.friendlyShow(dtBeg);
        System.out.print(v);
    }
}
