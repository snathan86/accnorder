package ebi.sd.accnorder.service.impl;

import ebi.sd.accnorder.model.AccnGroupResponse;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by senthil on 10/7/17.
 */
public class AccnGroupServiceImplTest {

    @Test
    public void testOrder() {
        AccnGroupServiceImpl service = new AccnGroupServiceImpl();

        String input = "A00000, A0001, ERR000111, ERR000112, ERR000113, ERR000115, ERR000116, ERR100114, " +
                "ERR200000001, ERR200000002, ERR200000003, DRR2110012, SRR211001, ABCDEFG1";
        AccnGroupResponse response =service.groupAccnNumbers(input);
        String expected = "A00000,A0001,ABCDEFG1,DRR2110012,ERR000111-ERR000113,ERR000115-ERR000116,ERR100114,ERR200000001-ERR200000003";
        Assert.assertEquals(expected,response.toString());

    }

    @Test(expected = IllegalStateException.class)
    public void testEmptyInput() {
        AccnGroupServiceImpl service = new AccnGroupServiceImpl();

        String input = "";
        AccnGroupResponse response =service.groupAccnNumbers(input);
        String expected = "A00000,A0001,ABCDEFG1,DRR2110012,ERR000111-ERR000113,ERR000115-ERR000116,ERR100114,ERR200000001-ERR200000003";
        Assert.assertEquals(expected,response.toString());

    }

    @Test
    public void testInvalidAccnNumbers() {
        AccnGroupServiceImpl service = new AccnGroupServiceImpl();

        //Invalid 20000120001, 12345ERR2, ERR2001AB
        Set<String> invalid = new HashSet<>();
        invalid.add("20000120001");
        invalid.add("12345ERR2");
        invalid.add("ERR2001AB");

        String input = "A00000, A0001, 20000120001, 12345ERR2, ERR2001AB, DRR2110012, SRR211001, ABCDEFG1";
        AccnGroupResponse response =service.groupAccnNumbers(input);

        response.getInvalidAccns().forEach( iv -> {
            Assert.assertTrue(invalid.contains(iv));
                }
        );
        Assert.assertEquals(3,response.getInvalidAccns().size());
    }

    @Test
    public void testIgnoreDuplicates() {
        AccnGroupServiceImpl service = new AccnGroupServiceImpl();

        String input = "A00000, A0001, A0001";
        AccnGroupResponse response =service.groupAccnNumbers(input);

        Assert.assertEquals(2,response.getGroupedAccns().size());
        Assert.assertEquals("A00000",response.getGroupedAccns().get(0));
        Assert.assertEquals("A0001",response.getGroupedAccns().get(1));
    }

    @Test(expected = IllegalStateException.class)
    public void testNullInput() {
        AccnGroupServiceImpl service = new AccnGroupServiceImpl();

        String input = "";
        AccnGroupResponse response =service.groupAccnNumbers(input);
        String expected = "A00000,A0001,ABCDEFG1,DRR2110012,ERR000111-ERR000113,ERR000115-ERR000116,ERR100114,ERR200000001-ERR200000003";
        Assert.assertEquals(expected,response.toString());

    }
}