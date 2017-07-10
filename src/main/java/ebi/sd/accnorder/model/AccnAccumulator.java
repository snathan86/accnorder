package ebi.sd.accnorder.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by senthil on 9/7/17.
 */
public class AccnAccumulator  {
    public int start;
    public int end;
    public String startStr;
    public String endStr;
    public String firstPart;
    public String accnNumber;

    public List<String> result = new ArrayList<>();
    public List<String> faultInput = new ArrayList<>();

}
