import axios from "axios";

const APP_URL = "http://localhost:8060/api/v1/weather";

class WeatherService {

    getAllWeather() {
        return axios.get(APP_URL);
    }

    deleteWeather(id){
        return axios.delete(`${APP_URL}/${id}`);
    }

    createWeather(weather){
        return axios.post(APP_URL,weather);
    }
}

//eslint-disable-next-line
export default new WeatherService();