package ebi.sd.accnorder.model;

import java.util.List;

/**
 * Created by senthil on 10/7/17.
 */
public class AccnGroupResponse {

    private List<String> groupedAccns;
    private List<String> invalidAccns;

    public void setGroupedAccns(List<String> groupedAccns) {
        this.groupedAccns = groupedAccns;
    }

    public List<String> getGroupedAccns() {
        return groupedAccns;
    }

    public List<String> getInvalidAccns() {
        return invalidAccns;
    }

    public void setInvalidAccns(List<String> invalidAccns) {
        this.invalidAccns = invalidAccns;
    }

    public String toString() {
        if (null != groupedAccns) {
            return groupedAccns.stream().reduce((x, y) -> x + "," + y).orElse("");
        }
        return "";
    }


}
