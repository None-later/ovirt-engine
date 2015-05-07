package org.ovirt.engine.core.bll.provider.storage;

import org.ovirt.engine.core.common.AuditLogType;
import org.ovirt.engine.core.common.action.LibvirtSecretParameters;
import org.ovirt.engine.core.common.errors.VdcBllMessages;

public class UpdateLibvirtSecretCommand extends LibvirtSecretCommandBase {

    public UpdateLibvirtSecretCommand(LibvirtSecretParameters parameters) {
        super(parameters);
    }

    @Override
    protected boolean canDoAction() {
        LibvirtSecretValidator libvirtSecretValidator =
                new LibvirtSecretValidator(getParameters().getLibvirtSecret());
        return validate(libvirtSecretValidator.uuidExist())
                && validate(libvirtSecretValidator.valueNotEmpty())
                && validate(libvirtSecretValidator.providerExist());
    }

    @Override
    protected void executeCommand() {
        super.executeCommand();
        getLibvirtSecretDAO().update(getParameters().getLibvirtSecret());
        setSucceeded(true);
    }

    @Override
    public AuditLogType getAuditLogTypeValue() {
        return getSucceeded() ? AuditLogType.USER_UPDATE_LIBVIRT_SECRET : AuditLogType.USER_FAILED_TO_UPDATE_LIBVIRT_SECRET;
    }

    @Override
    protected void setActionMessageParameters() {
        super.setActionMessageParameters();
        addCanDoActionMessage(VdcBllMessages.VAR__ACTION__UPDATE);
    }
}
