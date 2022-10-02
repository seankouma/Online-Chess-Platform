export function latLngToText(latLng, precision = 2) {
    return latLng ? `${latLng.lat.toFixed(precision)}, ${latLng.lng.toFixed(precision)}` : "";
}

export function placeToLatLng(place) {
    return place ? { lat: parseFloat(place.latitude), lng: parseFloat(place.longitude) } : place;
}

export function latLngToPlace(latLng) {
    return latLng ? { latitude: latLng.lat.toString(), longitude: latLng.lng.toString() } : latLng;
}