import { useState } from "react";
import planet1 from "../assets/image/serverIcons/server_planet_icon1.png";
import planet2 from "../assets/image/serverIcons/server_planet_icon2.png";
import planet3 from "../assets/image/serverIcons/server_planet_icon3.png";
import planet4 from "../assets/image/serverIcons/server_planet_icon4.png";

const usePlanetIcon = () => {
  const [planetIcon, setPlanetIcon] = useState(null);

  const getRandomPlanetIcon = () => {
    const randomNumber = Math.floor(Math.random() * 4) + 1;
    switch (randomNumber) {
      case 1:
        setPlanetIcon(planet1);
        break;
      case 2:
        setPlanetIcon(planet2);
        break;
      case 3:
        setPlanetIcon(planet3);
        break;
      case 4:
        setPlanetIcon(planet4);
        break;
      default:
        setPlanet(planet1);
        break;
    }
  };

  return [planetIcon, getRandomPlanetIcon];
};

export default usePlanetIcon;
