package ebi.sd.accnorder.service;


import ebi.sd.accnorder.model.AccnGroupResponse;

/**
 * Created by senthil on 10/7/17.
 */
public interface AccnGroupService {
    AccnGroupResponse groupAccnNumbers(String accnString);
}
