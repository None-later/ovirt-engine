package org.ovirt.engine.core.bll.provider.storage;

import org.ovirt.engine.core.common.AuditLogType;
import org.ovirt.engine.core.common.action.LibvirtSecretParameters;
import org.ovirt.engine.core.common.errors.VdcBllMessages;

public class RemoveLibvirtSecretCommand extends LibvirtSecretCommandBase {

    public RemoveLibvirtSecretCommand(LibvirtSecretParameters parameters) {
        super(parameters);
    }

    @Override
    protected boolean canDoAction() {
        LibvirtSecretValidator libvirtSecretValidator =
                new LibvirtSecretValidator(getParameters().getLibvirtSecret());
        return validate(libvirtSecretValidator.uuidExist());
    }

    @Override
    protected void executeCommand() {
        super.executeCommand();
        getLibvirtSecretDAO().remove(getParameters().getLibvirtSecret().getId());
        setSucceeded(true);
    }

    @Override
    public AuditLogType getAuditLogTypeValue() {
        return getSucceeded() ? AuditLogType.USER_REMOVED_LIBVIRT_SECRET : AuditLogType.USER_FAILED_TO_REMOVE_LIBVIRT_SECRET;
    }

    @Override
    protected void setActionMessageParameters() {
        super.setActionMessageParameters();
        addCanDoActionMessage(VdcBllMessages.VAR__ACTION__REMOVE);
    }
}
