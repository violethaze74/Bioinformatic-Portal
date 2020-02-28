package org.cbioportal.persistence;

import org.cbioportal.model.ClinicalEventSample;
import org.cbioportal.model.Treatment;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TreatmentRepository {
    public Map<String, List<Treatment>> getTreatmentsByPatient(List<String> sampleIds, List<String> studyIds);
    
    public Map<String, List<ClinicalEventSample>> getSamplesByPatient(List<String> sampleIds, List<String> studyIds);

    Set<String> getAllUniqueTreatments(List<String> sampleIds, List<String> studyIds);
}
