import { useMemo } from "react";

const categories = [
  { name: "🔥66챌린지", value: "CHALLENGE66" },
  { name: "🍽️식단 인증", value: "FOOD" },
  { name: "💪오운완", value: "TODAY" },
  { name: "🌞미라클모닝", value: "MIRACLE" },
  { name: "🏋️‍♀칼로리챌린지", value: "CALORIE" },
  { name: "🚶‍♀️만보챌린지", value: "MANBO" },
];

const useCategoryName = (categoryValue) => {
  return useMemo(() => {
    const category = categories.find((cat) => cat.value === categoryValue);
    return category ? category.name : "Unknown Category";
  }, [categoryValue]);
};

export default useCategoryName;
