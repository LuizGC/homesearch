(function () {
    async function findGoodAddresses() {
        const city = getCity();
        if (!city?.length) {
            alert('Please select a city');
            return;
        }
        const regions = await fetch(`/find/${city}/addresses`);
        console.debug(regions);

    }

    function getCity() {
        var citySelect = document.getElementById("city-select");
        return citySelect.value;
    }

    window.findGoodAddresses = findGoodAddresses;
})();
