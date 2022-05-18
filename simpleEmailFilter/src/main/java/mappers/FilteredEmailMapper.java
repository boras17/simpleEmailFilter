package mappers;

import fxmodels.FilteredEmailFX;
import models.filters.FilteredEmail;

public class FilteredEmailMapper {
    public static FilteredEmail fromFilteredEmailFXtoFilteredEmail(FilteredEmailFX filteredEmailFX) {
        FilteredEmail filteredEmail = new FilteredEmail();
        filteredEmail.setEmail(filteredEmailFX.getEmail());
        filteredEmail.setHost(filteredEmailFX.getHost());
        filteredEmail.setPassword(filteredEmailFX.getPassword());
        return filteredEmail;
    }
}
