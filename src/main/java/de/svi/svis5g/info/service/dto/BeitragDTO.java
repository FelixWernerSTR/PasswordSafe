package de.svi.svis5g.info.service.dto;

import de.svi.svis5g.info.domain.enumeration.Archiv;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link de.svi.svis5g.info.domain.Beitrag} entity.
 */
@ApiModel(description = "not an ignored comment")
public class BeitragDTO implements Serializable {

    private Long id;

    @Lob
    private String content;

    @Size(max = 200)
    private String attrib;

    @Size(max = 70)
    private String titel;

    @Size(max = 30)
    private String rechte;

    private LocalDate validFrom;

    private LocalDate validTo;

    private LocalDate publishDate;

    private Archiv archiv;

    private ThemaDTO thema;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttrib() {
        return attrib;
    }

    public void setAttrib(String attrib) {
        this.attrib = attrib;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getRechte() {
        return rechte;
    }

    public void setRechte(String rechte) {
        this.rechte = rechte;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public Archiv getArchiv() {
        return archiv;
    }

    public void setArchiv(Archiv archiv) {
        this.archiv = archiv;
    }

    public ThemaDTO getThema() {
        return thema;
    }

    public void setThema(ThemaDTO thema) {
        this.thema = thema;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BeitragDTO)) {
            return false;
        }

        BeitragDTO beitragDTO = (BeitragDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, beitragDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BeitragDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", attrib='" + getAttrib() + "'" +
            ", titel='" + getTitel() + "'" +
            ", rechte='" + getRechte() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", publishDate='" + getPublishDate() + "'" +
            ", archiv='" + getArchiv() + "'" +
            ", thema=" + getThema() +
            "}";
    }
}
