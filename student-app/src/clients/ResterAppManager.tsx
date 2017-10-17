interface Movie {
    Title: string;
}

export class ResterAppManager {
      
    public async fetchStudents(): Promise<any> {
        try {
            let response: Response = await fetch('http://www.omdbapi.com/?t=The Matrix');
            let movie: Movie = await response.json();
            console.log(movie);
            return movie;
        } catch (err) {
            console.log(err.message);
        }
    }

}