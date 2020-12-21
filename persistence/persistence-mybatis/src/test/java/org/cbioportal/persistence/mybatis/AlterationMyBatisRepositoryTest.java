package org.cbioportal.persistence.mybatis;

import org.cbioportal.model.*;
import org.cbioportal.model.util.Select;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/testContextDatabase.xml")
@Configurable
public class AlterationMyBatisRepositoryTest {

//    mutation and cna events in testSql.sql
//        SAMPLE_ID, ENTREZ_GENE_ID, HUGO_GENE_SYMBOL, GENETIC_PROFILE_ID, MUTATION_TYPE, DRIVER_FILTER, DRIVER_TIERS_FILTER, PATIENT_ID
//        1	    207	AKT1	2	-2	                Putative_Driver	    Tier 1  TCGA-A1-A0SB
//        2	    207	AKT1	2	2	                Putative_Passenger	Tier 2  TCGA-A1-A0SD
//        1	    207	AKT1	6	Nonsense_Mutation	Putative_Driver	    Tier 1  TCGA-A1-A0SB
//        2	    207	AKT1	6	Missense_Mutation	Putative_Passenger	Tier 2  TCGA-A1-A0SD
//        1	    208	AKT2	2	2		            <null>              <null>  TCGA-A1-A0SB
//        3	    208	AKT2	6	Splice_Site	        Putative_Passenger	Tier 1  TCGA-A1-A0SE
//        6	    672	BRCA1	6	Missense_Mutation	Putative_Passenger	Tier 2  TCGA-A1-A0SH
//        6	    672	BRCA1	6	Nonsense_Mutation	Putative_Driver	    Tier 1  TCGA-A1-A0SH
//        7	    672	BRCA1	6	Nonsense_Mutation	Putative_Driver	    Tier 2  TCGA-A1-A0SI
//        12	672	BRCA1	6	Splice_Site	        Putative_Passenger	Tier 1  TCGA-A1-A0SO
//        13	672	BRCA1	6	Splice_Site	        Putative_Driver	    Tier 1  TCGA-A1-A0SP

    @Autowired
    private AlterationMyBatisRepository alterationMyBatisRepository;

    Select<MutationEventType> mutationEventTypes = Select.byValues(Arrays.asList(
        MutationEventType.splice_site,
        MutationEventType.nonsense_mutation,
        MutationEventType.missense_mutation
    ));
    Select<CopyNumberAlterationEventType> cnaEventTypes = Select.byValues(Arrays.asList(
        CopyNumberAlterationEventType.AMP,
        CopyNumberAlterationEventType.HOMDEL
    ));
    List<MolecularProfileCaseIdentifier> sampleIdToProfileId = new ArrayList<>();
    List<MolecularProfileCaseIdentifier> patientIdToProfileId = new ArrayList<>();
    List<Integer> entrezGeneIds = new ArrayList<>();
    boolean searchFusions = false;

    @Before
    public void setup() {
        
        sampleIdToProfileId.add(new MolecularProfileCaseIdentifier("TCGA-A1-A0SB-01", "study_tcga_pub_mutations"));
        sampleIdToProfileId.add(new MolecularProfileCaseIdentifier("TCGA-A1-A0SE-01", "study_tcga_pub_mutations"));
        sampleIdToProfileId.add(new MolecularProfileCaseIdentifier("TCGA-A1-A0SH-01", "study_tcga_pub_mutations"));
        sampleIdToProfileId.add(new MolecularProfileCaseIdentifier("TCGA-A1-A0SI-01", "study_tcga_pub_mutations"));
        sampleIdToProfileId.add(new MolecularProfileCaseIdentifier("TCGA-A1-A0SO-01", "study_tcga_pub_mutations"));
        sampleIdToProfileId.add(new MolecularProfileCaseIdentifier("TCGA-A1-A0SP-01", "study_tcga_pub_mutations"));
        sampleIdToProfileId.add(new MolecularProfileCaseIdentifier("TCGA-A1-A0SD-01", "study_tcga_pub_mutations"));
        sampleIdToProfileId.add(new MolecularProfileCaseIdentifier("TCGA-A1-A0SB-01", "study_tcga_pub_gistic"));
        sampleIdToProfileId.add(new MolecularProfileCaseIdentifier("TCGA-A1-A0SD-01", "study_tcga_pub_gistic"));

        patientIdToProfileId.add(new MolecularProfileCaseIdentifier("TCGA-A1-A0SB", "study_tcga_pub_mutations"));
        patientIdToProfileId.add(new MolecularProfileCaseIdentifier("TCGA-A1-A0SE", "study_tcga_pub_mutations"));
        patientIdToProfileId.add(new MolecularProfileCaseIdentifier("TCGA-A1-A0SH", "study_tcga_pub_mutations"));
        patientIdToProfileId.add(new MolecularProfileCaseIdentifier("TCGA-A1-A0SI", "study_tcga_pub_mutations"));
        patientIdToProfileId.add(new MolecularProfileCaseIdentifier("TCGA-A1-A0SO", "study_tcga_pub_mutations"));
        patientIdToProfileId.add(new MolecularProfileCaseIdentifier("TCGA-A1-A0SP", "study_tcga_pub_mutations"));
        patientIdToProfileId.add(new MolecularProfileCaseIdentifier("TCGA-A1-A0SD", "study_tcga_pub_mutations"));
        patientIdToProfileId.add(new MolecularProfileCaseIdentifier("TCGA-A1-A0SB", "study_tcga_pub_gistic"));
        patientIdToProfileId.add(new MolecularProfileCaseIdentifier("TCGA-A1-A0SD", "study_tcga_pub_gistic"));
        
        entrezGeneIds.add(207);
        entrezGeneIds.add(208);
        entrezGeneIds.add(672);
    }

    @Test
    public void getSampleMutationCount() throws Exception {

        cnaEventTypes = Select.none();
        List<AlterationCountByGene> result = alterationMyBatisRepository.getSampleAlterationCounts(
            sampleIdToProfileId, entrezGeneIds, mutationEventTypes, cnaEventTypes, searchFusions);

        Assert.assertEquals(3, result.size());
        AlterationCountByGene result672 = result.stream().filter(r -> r.getEntrezGeneId() == 672).findFirst().get();
        AlterationCountByGene result207 = result.stream().filter(r -> r.getEntrezGeneId() == 207).findFirst().get();
        AlterationCountByGene result208 = result.stream().filter(r -> r.getEntrezGeneId() == 208).findFirst().get();
        Assert.assertEquals((Integer) 5, result672.getTotalCount());
        Assert.assertEquals((Integer) 4, result672.getNumberOfAlteredCases());
        Assert.assertEquals((Integer) 2, result207.getTotalCount());
        Assert.assertEquals((Integer) 2, result207.getNumberOfAlteredCases());
        Assert.assertEquals((Integer) 1, result208.getTotalCount());
        Assert.assertEquals((Integer) 1, result208.getNumberOfAlteredCases());
    }

    @Test
    public void getSampleCnaCount() throws Exception {

        mutationEventTypes = Select.none();
        List<AlterationCountByGene> result = alterationMyBatisRepository.getSampleAlterationCounts(
            sampleIdToProfileId, entrezGeneIds, mutationEventTypes, cnaEventTypes, searchFusions);

        Assert.assertEquals(2, result.size());
        AlterationCountByGene result207 = result.stream().filter(r -> r.getEntrezGeneId() == 207).findFirst().get();
        AlterationCountByGene result208 = result.stream().filter(r -> r.getEntrezGeneId() == 208).findFirst().get();
        Assert.assertEquals((Integer) 2, result207.getTotalCount());
        Assert.assertEquals((Integer) 2, result207.getNumberOfAlteredCases());
        Assert.assertEquals((Integer) 1, result208.getTotalCount());
        Assert.assertEquals((Integer) 1, result208.getNumberOfAlteredCases());
    }

    @Test
    public void getSampleMutationAndCnaCount() throws Exception {

        List<AlterationCountByGene> result = alterationMyBatisRepository.getSampleAlterationCounts(
            sampleIdToProfileId, entrezGeneIds, mutationEventTypes, cnaEventTypes, searchFusions);

        Assert.assertEquals(3, result.size());
        AlterationCountByGene result672 = result.stream().filter(r -> r.getEntrezGeneId() == 672).findFirst().get();
        AlterationCountByGene result207 = result.stream().filter(r -> r.getEntrezGeneId() == 207).findFirst().get();
        AlterationCountByGene result208 = result.stream().filter(r -> r.getEntrezGeneId() == 208).findFirst().get();
        Assert.assertEquals((Integer) 5, result672.getTotalCount());
        Assert.assertEquals((Integer) 4, result672.getNumberOfAlteredCases());
        Assert.assertEquals((Integer) 4, result207.getTotalCount());
        Assert.assertEquals((Integer) 2, result207.getNumberOfAlteredCases());
        Assert.assertEquals((Integer) 2, result208.getTotalCount());
        Assert.assertEquals((Integer) 2, result208.getNumberOfAlteredCases());
    }

    @Test
    public void getSampleMutationCountFilterFusions() throws Exception {

        searchFusions = true;

        cnaEventTypes = Select.none();
        List<AlterationCountByGene> result = alterationMyBatisRepository.getSampleAlterationCounts(
            sampleIdToProfileId, entrezGeneIds, mutationEventTypes, cnaEventTypes, searchFusions);
        // there are no fusion mutations in the test db
        Assert.assertEquals(0, result.size());
    }

    @Test
    public void getPatientMutationCount() throws Exception {

        cnaEventTypes = Select.none();
        List<AlterationCountByGene> result = alterationMyBatisRepository.getPatientAlterationCounts(
            patientIdToProfileId, entrezGeneIds, mutationEventTypes, cnaEventTypes, searchFusions);

        // For testSql.sql there are no more samples per patient for the investigated genes.
        // Therefore, patient level counts are the same as the sample level counts.
        Assert.assertEquals(3, result.size());
        AlterationCountByGene result672 = result.stream().filter(r -> r.getEntrezGeneId() == 672).findFirst().get();
        AlterationCountByGene result207 = result.stream().filter(r -> r.getEntrezGeneId() == 207).findFirst().get();
        AlterationCountByGene result208 = result.stream().filter(r -> r.getEntrezGeneId() == 208).findFirst().get();
        Assert.assertEquals((Integer) 5, result672.getTotalCount());
        Assert.assertEquals((Integer) 4, result672.getNumberOfAlteredCases());
        Assert.assertEquals((Integer) 2, result207.getTotalCount());
        Assert.assertEquals((Integer) 2, result207.getNumberOfAlteredCases());
        Assert.assertEquals((Integer) 1, result208.getTotalCount());
        Assert.assertEquals((Integer) 1, result208.getNumberOfAlteredCases());
    }

    @Test
    public void getPatientCnaCount() throws Exception {

        mutationEventTypes = Select.none();
        List<AlterationCountByGene> result = alterationMyBatisRepository.getPatientAlterationCounts(
            patientIdToProfileId, entrezGeneIds, mutationEventTypes, cnaEventTypes, searchFusions);

        // For testSql.sql there are no more samples per patient for the investigated genes.
        // Therefore, patient level counts are the same as the sample level counts.
        Assert.assertEquals(2, result.size());
        AlterationCountByGene result207 = result.stream().filter(r -> r.getEntrezGeneId() == 207).findFirst().get();
        AlterationCountByGene result208 = result.stream().filter(r -> r.getEntrezGeneId() == 208).findFirst().get();
        Assert.assertEquals((Integer) 2, result207.getTotalCount());
        Assert.assertEquals((Integer) 2, result207.getNumberOfAlteredCases());
        Assert.assertEquals((Integer) 1, result208.getTotalCount());
        Assert.assertEquals((Integer) 1, result208.getNumberOfAlteredCases());
    }

    @Test
    public void getPatientMutationAndCnaCount() throws Exception {

        List<AlterationCountByGene> result = alterationMyBatisRepository.getPatientAlterationCounts(
            patientIdToProfileId, entrezGeneIds, mutationEventTypes, cnaEventTypes, searchFusions);

        // For testSql.sql there are no more samples per patient for the investigated genes.
        // Therefore, patient level counts are the same as the sample level counts.
        Assert.assertEquals(3, result.size());
        AlterationCountByGene result672 = result.stream().filter(r -> r.getEntrezGeneId() == 672).findFirst().get();
        AlterationCountByGene result207 = result.stream().filter(r -> r.getEntrezGeneId() == 207).findFirst().get();
        AlterationCountByGene result208 = result.stream().filter(r -> r.getEntrezGeneId() == 208).findFirst().get();
        Assert.assertEquals((Integer) 5, result672.getTotalCount());
        Assert.assertEquals((Integer) 4, result672.getNumberOfAlteredCases());
        Assert.assertEquals((Integer) 4, result207.getTotalCount());
        Assert.assertEquals((Integer) 2, result207.getNumberOfAlteredCases());
        Assert.assertEquals((Integer) 2, result208.getTotalCount());
        Assert.assertEquals((Integer) 2, result208.getNumberOfAlteredCases());
    }

    @Test
    public void getPatientMutationCountFilterFusions() throws Exception {

        searchFusions = true;
        cnaEventTypes = Select.none();
        List<AlterationCountByGene> result = alterationMyBatisRepository.getPatientAlterationCounts(
            patientIdToProfileId, entrezGeneIds, mutationEventTypes, cnaEventTypes, searchFusions);
        // there are no fusion mutations in the test db
        Assert.assertEquals(0, result.size());
    }

    @Test
    public void getSampleCnaCountLegacy() throws Exception {

        // FIXME: whole CNA dedicated endpoint should be removed
        entrezGeneIds = null; // only way it works; otherwise it tries to pair up geneids with alteration types with
        List<CopyNumberCountByGene> result = alterationMyBatisRepository.getSampleCnaCounts(
            sampleIdToProfileId, entrezGeneIds, cnaEventTypes);

        Assert.assertEquals(3, result.size());
        AlterationCountByGene result207up = result.stream().filter(r -> r.getEntrezGeneId() == 207 && r.getAlteration() == 2).findFirst().get();
        AlterationCountByGene result207down = result.stream().filter(r -> r.getEntrezGeneId() == 207 && r.getAlteration() == -2).findFirst().get();
        AlterationCountByGene result208up = result.stream().filter(r -> r.getEntrezGeneId() == 208).findFirst().get();
        Assert.assertEquals((Integer) 1, result207up.getNumberOfAlteredCases());
        Assert.assertEquals((Integer) 1, result207down.getNumberOfAlteredCases());
        Assert.assertEquals((Integer) 1, result208up.getNumberOfAlteredCases());
    }
    
    @Test
    public void getPatientCnaCountLegacy() throws Exception {

        // FIXME: whole CNA dedicated endpoint should be removed
        entrezGeneIds = null; // only way it works; otherwise it tries to pair up geneids with alteration types with
        List<CopyNumberCountByGene> result = alterationMyBatisRepository.getPatientCnaCounts(
            patientIdToProfileId, entrezGeneIds, cnaEventTypes);

        // For testSql.sql there are no more samples per patient for the investigated genes.
        // Therefore, patient level counts are the same as the sample level counts.
        Assert.assertEquals(3, result.size());
        AlterationCountByGene result207up = result.stream().filter(r -> r.getEntrezGeneId() == 207 && r.getAlteration() == 2).findFirst().get();
        AlterationCountByGene result207down = result.stream().filter(r -> r.getEntrezGeneId() == 207 && r.getAlteration() == -2).findFirst().get();
        AlterationCountByGene result208up = result.stream().filter(r -> r.getEntrezGeneId() == 208).findFirst().get();
        Assert.assertEquals((Integer) 1, result207up.getNumberOfAlteredCases());
        Assert.assertEquals((Integer) 1, result207down.getNumberOfAlteredCases());
        Assert.assertEquals((Integer) 1, result208up.getNumberOfAlteredCases());
    }

    @Test
    public void getSampleAlterationCountsReturnsZeroForMutationsAndCnaSelectorsInNone() {

        mutationEventTypes = Select.none();
        cnaEventTypes = Select.none();
        List<AlterationCountByGene> result = alterationMyBatisRepository.getSampleAlterationCounts(
            sampleIdToProfileId, entrezGeneIds, mutationEventTypes, cnaEventTypes, searchFusions);

        Assert.assertEquals(0, result.size());
    }

    @Test
    public void getSampleAlterationCountsReturnsAllForMutationsAndCnaSelectorsInAll() {

        mutationEventTypes = Select.all();
        cnaEventTypes = Select.all();
        List<AlterationCountByGene> result = alterationMyBatisRepository.getSampleAlterationCounts(
            sampleIdToProfileId, entrezGeneIds, mutationEventTypes, cnaEventTypes, searchFusions);

        Assert.assertEquals(3, result.size());
    }
}
