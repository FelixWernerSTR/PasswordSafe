package de.fw.passwordsafe.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link de.fw.passwordsafe.domain.Entry} entity.
 */
public class EntryDTO implements Serializable {

    private Long id;

    @Size(max = 250)
    private String description;

    @NotNull
    @Size(max = 250)
    private String login;

    @NotNull
    @Size(max = 250)
    private String passwort;

    @NotNull
    @Size(max = 250)
    private String passwortReplay;

    private GroupDTO group;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getPasswortReplay() {
        return passwortReplay;
    }

    public void setPasswortReplay(String passwortReplay) {
        this.passwortReplay = passwortReplay;
    }

    public GroupDTO getGroup() {
        return group;
    }

    public void setGroup(GroupDTO group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntryDTO)) {
            return false;
        }

        EntryDTO entryDTO = (EntryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, entryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EntryDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", login='" + getLogin() + "'" +
            ", passwort='" + getPasswort() + "'" +
            ", passwortReplay='" + getPasswortReplay() + "'" +
            ", group=" + getGroup() +
            "}";
    }
}
