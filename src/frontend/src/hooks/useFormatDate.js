const useFormatDate = (dateString) => {
  if (!dateString) return "";

  const date = new Date(dateString);
  if (isNaN(date.getTime())) return "";

  const today = new Date();
  const yesterday = new Date(today);
  yesterday.setDate(yesterday.getDate() - 1);

  const hours = date.getHours();
  const minutes =
    date.getMinutes() < 10 ? `0${date.getMinutes()}` : date.getMinutes();

  if (date.toDateString() === today.toDateString()) {
    return `오늘 ${hours}:${minutes}`;
  } else if (date.toDateString() === yesterday.toDateString()) {
    return `어제 ${hours}:${minutes}`;
  } else {
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, "0");
    const day = date.getDate().toString().padStart(2, "0");
    return `${year}. ${month}. ${day} ${hours}:${minutes}`;
  }
};

export default useFormatDate;
