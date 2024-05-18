const useFormatDate = (dateString) => {
  const date = new Date(dateString);
  const today = new Date();
  const yesterday = new Date(today);
  yesterday.setDate(yesterday.getDate() - 1);

  if (date.toDateString() === today.toDateString()) {
    return `오늘 ${date.getHours()}:${(date.getMinutes() < 10 ? "0" : "") + date.getMinutes()}`;
  } else if (date.toDateString() === yesterday.toDateString()) {
    return `어제 ${date.getHours()}:${(date.getMinutes() < 10 ? "0" : "") + date.getMinutes()}`;
  } else {
    const year = date.getFullYear();
    const month = (date.getMonth() + 1 < 10 ? "0" : "") + (date.getMonth() + 1);
    const day = (date.getDate() < 10 ? "0" : "") + date.getDate();
    return `${year}. ${month}. ${day} ${date.getHours()}:${(date.getMinutes() < 10 ? "0" : "") + date.getMinutes()}`;
  }
};
export default useFormatDate;
