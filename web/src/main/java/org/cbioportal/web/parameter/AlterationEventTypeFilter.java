package org.cbioportal.web.parameter;

import org.cbioportal.model.CopyNumberAlterationEventType;
import org.cbioportal.model.MutationEventType;

import java.util.List;
import java.util.Map;

public class AlterationEventTypeFilter {
    
    private Map<MutationEventType, Boolean> mutationEventTypes;
    private Map<CopyNumberAlterationEventType, Boolean> copyNumberAlterationEventTypes;

    public Map<MutationEventType, Boolean> getMutationEventTypes() {
        return mutationEventTypes;
    }

    public void setMutationEventTypes(Map<MutationEventType, Boolean> mutationEventTypes) {
        this.mutationEventTypes = mutationEventTypes;
    }

    public Map<CopyNumberAlterationEventType, Boolean> getCopyNumberAlterationEventTypes() {
        return copyNumberAlterationEventTypes;
    }

    public void setCopyNumberAlterationEventTypes(Map<CopyNumberAlterationEventType, Boolean> copyNumberAlterationEventTypes) {
        this.copyNumberAlterationEventTypes = copyNumberAlterationEventTypes;
    }
    
}
