package partner.services;

import partner.models.Partner;
import storage.StorageService;

public class PartnerServiceImpl implements PartnerService {
    private final StorageService storageService;

    public PartnerServiceImpl(StorageService storageService) {
        this.storageService = storageService;
    }

    public boolean register(Partner partner) {
        this.storageService.savePartner(partner);
        return true;
    }
}
