

export default function formatDate(now) {
    function addZero(m) {
        return m<10 ? '0'+m : m
    }
    const year = now.getFullYear();
    const month = now.getMonth()+1;
    const day = now.getDate() ;
    const hour = now.getHours();
    const minute = now.getMinutes();


}
