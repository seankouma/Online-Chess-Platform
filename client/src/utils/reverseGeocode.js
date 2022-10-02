const GEOCODE_URL = "https://nominatim.openstreetmap.org/reverse?format=jsonv2";

async function reverseGeocode(latLng) {
    // Here the coordinates are in latLng format - example: {lat: 0, lng: 0}
    const API_URL = GEOCODE_URL + `&lat=${latLng.lat}&lon=${latLng.lng}`;
    const data = await fetch(API_URL);
    const dataJSON = await data.json();

    if (dataJSON.display_name) {
        return {...latLng, name: dataJSON.display_name};
    }
    return {...latLng, name: "Unknown"};
}

export {reverseGeocode};