/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hotels.util;

/**
 *
 * @author Olyjosh
 */
public class CountryModel {
    private String code;
    private String countryName;
    

    public CountryModel(String code, String countryName) {
        this.code = code;
        this.countryName = countryName;
    }

    public String getCode() {
        return code;
    }

    public String getCountryName() {
        return countryName;
    }

    @Override
    public String toString(){
        return this.countryName;
    }
    
}
