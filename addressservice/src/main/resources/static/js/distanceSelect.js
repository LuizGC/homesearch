(function () {
    function populateDistanceSelect(selectElement) {
        selectElement.add(createOption("Any distance", ""));
        selectElement.add(createOption("10 minutes", "700"));
        selectElement.add(createOption("20 minutes", "1400"));
        selectElement.add(createOption("30 minutes", "2100"));
    }

    function createOption(text, value) {
        var option = document.createElement("option");
        option.text = text;
        option.value = value;
        return option;
    }

    window.populateDistanceSelect = populateDistanceSelect;
})();
