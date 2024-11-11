function initMap(mapElementId) {
    const map = L.map(mapElementId, {
        attributionControl: false,
        zoomControl: false
    });

    const baseMap = L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '<a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    });

    map.setView([51.7338, 19.2041], 5);

    map.addLayer(baseMap);

    return map;
}
