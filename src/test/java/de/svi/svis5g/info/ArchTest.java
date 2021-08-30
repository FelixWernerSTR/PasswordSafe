package de.svi.svis5g.info;

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
            .importPackages("de.svi.svis5g.info");

        noClasses()
            .that()
            .resideInAnyPackage("de.svi.svis5g.info.service..")
            .or()
            .resideInAnyPackage("de.svi.svis5g.info.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..de.svi.svis5g.info.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
