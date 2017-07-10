package ebi.sd.accnorder.service.impl;

import ebi.sd.accnorder.model.AccessionNumbers;
import ebi.sd.accnorder.model.AccnAccumulator;
import ebi.sd.accnorder.model.AccnGroupResponse;
import ebi.sd.accnorder.service.AccnGroupService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by senthil on 10/7/17.
 */
@Service
public class AccnGroupServiceImpl implements AccnGroupService {

    static Pattern pattern = Pattern.compile("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");

    @Override
    public AccnGroupResponse groupAccnNumbers(final String accnString) {

        AccnAccumulator accumulator = null;

        System.out.println("accnString: " + accnString);
        if (null != accnString && !StringUtils.isEmpty(accnString.trim())) {
            List<String> accnNoList = Arrays.stream(accnString.split(",")).map(String::trim).collect(Collectors.toList());
            accumulator = accnNoList.stream().sorted()
                    .reduce(new AccnAccumulator(), AccnGroupServiceImpl::order, ((accm, accm1) -> accm));
        } else {
            throw new IllegalStateException("Invalid input");
        }


        AccnGroupResponse acn = new AccnGroupResponse();


        acn.setGroupedAccns(accumulator.result);
        acn.setInvalidAccns(accumulator.faultInput);
        return acn;
    }

    private static AccnAccumulator order(final AccnAccumulator acc, final String accNumber) {
        {
            final String strAndNumber[] = pattern.split(accNumber);

            //TODO: only validation happens here, create a separate validator class and call Validator.validate(String)
            //TODO:  instead of below if condition
            if (strAndNumber.length == 2) {
                try {
                    //    Integer.parseInt(strAndNumber[1]);


                    //this will be executed only one(first) time
                    if (null == acc.startStr) {
                        acc.start = Integer.parseInt(strAndNumber[1]);
                        acc.startStr = strAndNumber[1];
                        acc.firstPart = strAndNumber[0];
                    } else if (acc.firstPart.equalsIgnoreCase(strAndNumber[0]) && strAndNumber[1].length() == acc.startStr.length()) {
                        //firstPart of accnNumber should be same for both the numbers if it is consecutive

                        if (null == acc.endStr) {
                            //No need to worry about NumberFormatException as the regEx pattern splits string and int
                            final int temp = Integer.parseInt(strAndNumber[1]);
                            //TODO: given that all numbers are unique but safer side check for temp-startNo == 0 , duplicate
                            //it proves the numbers are consecutive
                            if (temp - acc.start == 1) {
                                acc.endStr = strAndNumber[1];
                                acc.end = temp;
                            } else {
                                acc.result.add(acc.firstPart + acc.startStr);
                                acc.startStr = strAndNumber[1];
                                acc.start = temp;
                            }
                        } else {
                            final int temp = Integer.parseInt(strAndNumber[1]);
                            if (temp - acc.end == 1) {
                                acc.end = temp;
                                acc.endStr = strAndNumber[1];
                            } else {
                                acc.result.add(acc.firstPart + acc.startStr + "-" + acc.firstPart + acc.endStr);
                                acc.firstPart = strAndNumber[0];
                                acc.startStr = strAndNumber[1];
                                acc.start = temp;
                                acc.endStr = null;
                            }
                        }
                    } else {
                        if (null == acc.endStr) {
                            acc.result.add(acc.firstPart + acc.startStr);
                            acc.start = Integer.parseInt(strAndNumber[1]);
                            acc.startStr = strAndNumber[1];
                            acc.firstPart = strAndNumber[0];
                        } else {
                            acc.result.add(acc.firstPart + acc.startStr + "-" + acc.firstPart + acc.endStr);
                            acc.start = Integer.parseInt(strAndNumber[1]);
                            acc.startStr = strAndNumber[1];
                            acc.firstPart = strAndNumber[0];
                            acc.endStr = null;
                        }
                    }
                } catch (NumberFormatException nfe) {
                    acc.faultInput.add(accNumber);
                }
            } else {
                acc.faultInput.add(accNumber);
            }
        }
        return acc;
    }
}
