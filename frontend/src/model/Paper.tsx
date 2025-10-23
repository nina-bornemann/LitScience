export type Paper = {
    id:string,
    doi:string,
    title:string,
    author:string,
    year:number,
    group:string,
    notes:string
}

export type PaperDto = {
    doi:string | undefined,
    title:string | undefined,
    author:string | undefined,
    year:number | undefined,
    group:string | undefined,
    notes:string | undefined
}

