package org.cbioportal.service;

import org.cbioportal.model.*;
import org.cbioportal.model.util.Select;

import java.util.List;

public interface AlterationCountService {

    List<AlterationCountByGene> getSampleAlterationCounts(List<MolecularProfileCaseIdentifier> molecularProfileCaseIdentifiers,
                                                          List<Integer> entrezGeneIds,
                                                          boolean includeFrequency,
                                                          boolean includeMissingAlterationsFromGenePanel,
                                                          Select<MutationEventType> mutationEventTypes,
                                                          Select<CopyNumberAlterationEventType> cnaEventTypes);

    List<AlterationCountByGene> getPatientAlterationCounts(List<MolecularProfileCaseIdentifier> molecularProfileCaseIdentifiers,
                                                           List<Integer> entrezGeneIds,
                                                           boolean includeFrequency,
                                                           boolean includeMissingAlterationsFromGenePanel,
                                                           Select<MutationEventType> mutationEventTypes,
                                                           Select<CopyNumberAlterationEventType> cnaEventTypes);

    List<AlterationCountByGene> getSampleMutationCounts(List<MolecularProfileCaseIdentifier> molecularProfileCaseIdentifiers,
                                                        List<Integer> entrezGeneIds,
                                                        boolean includeFrequency,
                                                        boolean includeMissingAlterationsFromGenePanel,
                                                        Select<MutationEventType> mutationEventTypes);

    List<AlterationCountByGene> getPatientMutationCounts(List<MolecularProfileCaseIdentifier> molecularProfileCaseIdentifiers,
                                                         List<Integer> entrezGeneIds,
                                                         boolean includeFrequency,
                                                         boolean includeMissingAlterationsFromGenePanel,
                                                         Select<MutationEventType> mutationEventTypes);

    List<AlterationCountByGene> getSampleFusionCounts(List<MolecularProfileCaseIdentifier> molecularProfileCaseIdentifiers,
                                                      List<Integer> entrezGeneIds,
                                                      boolean includeFrequency,
                                                      boolean includeMissingAlterationsFromGenePanel,
                                                      Select<MutationEventType> mutationEventTypes);

    List<AlterationCountByGene> getPatientFusionCounts(List<MolecularProfileCaseIdentifier> molecularProfileCaseIdentifiers,
                                                       List<Integer> entrezGeneIds,
                                                       boolean includeFrequency,
                                                       boolean includeMissingAlterationsFromGenePanel,
                                                       Select<MutationEventType> mutationEventTypes);

// Should be restored when old CNA count endpoint is retired
//    List<AlterationCountByGene> getSampleCnaCounts(List<MolecularProfileCaseIdentifier> molecularProfileCaseIdentifiers,
//                                                   List<Integer> entrezGeneIds,
//                                                   boolean includeFrequency,
//                                                   boolean includeMissingAlterationsFromGenePanel,
//                                                   List<CopyNumberAlterationEventType> cnaEventTypes);
//
//    List<AlterationCountByGene> getPatientCnaCounts(List<MolecularProfileCaseIdentifier> molecularProfileCaseIdentifiers,
//                                                    List<Integer> entrezGeneIds,
//                                                    boolean includeFrequency,
//                                                    boolean includeMissingAlterationsFromGenePanel,
//                                                    List<CopyNumberAlterationEventType> cnaEventTypes);
    
// Should be removed when old CNA count endpoint is retired
    List<CopyNumberCountByGene> getSampleCnaCounts(List<MolecularProfileCaseIdentifier> molecularProfileCaseIdentifiers,
                                                   List<Integer> entrezGeneIds,
                                                   boolean includeFrequency,
                                                   boolean includeMissingAlterationsFromGenePanel,
                                                   Select<CopyNumberAlterationEventType> cnaEventTypes);

    List<CopyNumberCountByGene> getPatientCnaCounts(List<MolecularProfileCaseIdentifier> molecularProfileCaseIdentifiers,
                                                    List<Integer> entrezGeneIds,
                                                    boolean includeFrequency,
                                                    boolean includeMissingAlterationsFromGenePanel,
                                                    Select<CopyNumberAlterationEventType> cnaEventTypes);
    
}
