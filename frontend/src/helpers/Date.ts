
export const mapDate = (dateStr: string): string => {
    if (dateStr.includes('ago')) {
        return dateStr
    }

    let date = new Date(dateStr)
    let now = new Date()
    
    if (date.getFullYear() < now.getFullYear()) {
        return `${now.getFullYear() - date.getFullYear()} year(s) ago`
    }

    if (date.getMonth() < now.getMonth()) {
        return `${now.getMonth() - date.getMonth()} month(s) ago`
    }
    
    if (date.getDate() < now.getDate()) {
        return `${now.getDate() - date.getDate()} day(s) ago`
    }

    if (date.getHours() < now.getHours()) {
        return `${now.getHours() - date.getHours()} hour(s) ago`
    }

    if (date.getMinutes() < now.getMinutes()) {
        return `${now.getMinutes() - date.getMinutes()} minute(s) ago`
    }
    
    return `${now.getSeconds() - date.getSeconds()} second(s) ago` 
}