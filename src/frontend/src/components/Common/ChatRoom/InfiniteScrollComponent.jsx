import React, { useEffect } from "react";
import InfiniteScroll from "react-infinite-scroll-component";

const InfiniteScrollComponent = ({
  children,
  dataLength,
  next,
  hasMore,
  scrollableTarget,
}) => {
  useEffect(() => {
    const handleScroll = () => {
      const windowHeight =
        "innerHeight" in window
          ? window.innerHeight
          : document.documentElement.offsetHeight;
      const body = document.body;
      const html = document.documentElement;
      const docHeight = Math.max(
        body.scrollHeight,
        body.offsetHeight,
        html.clientHeight,
        html.scrollHeight,
        html.offsetHeight
      );
      const windowBottom = windowHeight + window.pageYOffset;
      if (windowBottom >= docHeight && hasMore) {
        next();
      }
    };

    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, [next, hasMore]);

  return (
    <InfiniteScroll
      dataLength={dataLength}
      next={next}
      hasMore={hasMore}
      scrollableTarget={scrollableTarget}
    >
      {children}
    </InfiniteScroll>
  );
};

export default InfiniteScrollComponent;
