(function() {
    async function listCities() {
        const response = await fetch("/cities");
        return await response.json();
    }
    
    async function populateCitySelect(selectElement) {
        const cities = await listCities();
        selectElement.add(createOption("--Please choose an option--", ""));
        for(var city of cities) {
            selectElement.add(createOption(city, city));
        }
    }
    
    function createOption(text, value) {
        var option = document.createElement("option");
        option.text = text;
        option.value = value;
        return option;
    }

    window.populateCitySelect = populateCitySelect;
})();


