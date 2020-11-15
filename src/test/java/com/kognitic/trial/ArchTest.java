package com.kognitic.trial;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.kognitic.trial");

        noClasses()
            .that()
            .resideInAnyPackage("com.kognitic.trial.service..")
            .or()
            .resideInAnyPackage("com.kognitic.trial.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.kognitic.trial.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
