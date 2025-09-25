const prod = {
    url: {
        BASE_URL: 'https://library-service-961766531040.europe-west1.run.app/api' //backend url in cloud
    },
};

/*const dev = {
    url: {
        BASE_URL: 'http://localhost:8080/api/'
    },
};*/

export const config = prod; /*process.env.NODE_ENV === 'development' ? dev : prod;*/