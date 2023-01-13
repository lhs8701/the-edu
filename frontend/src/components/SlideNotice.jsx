import { useEffect, useState } from "react";
import { motion, AnimatePresence, wrap } from "framer-motion";
import { images } from "../dummy";
import styled from "styled-components";

const NoticeWrapper = styled(motion.div)`
  width: 100%;
  height: 300px;
  display: flex;
  position: relative;
  border-radius: var(--size-border-radius);
  overflow: hidden;
  margin-bottom: 50px;
  background-image: images[imageIndex];
`;

const Ad = styled(motion.img)`
  width: 100%;
  height: 100%;
  position: absolute;
  cursor: pointer;
`;

const Pagniation = styled(motion.div)`
  position: absolute;
  height: 100px;

  color: var(--color-background);
  top: 135px;
  cursor: pointer;
`;

const LeftPagnigation = styled(Pagniation)`
  left: 14px;
`;
const RightPagnigation = styled(Pagniation)`
  right: 14px;
`;

const variants = {
  enter: (direction) => {
    return {
      x: direction > 0 ? 1000 : -1000,
      opacity: 0,
    };
  },
  center: {
    x: 0,
    opacity: 1,
  },
  exit: (direction) => {
    return {
      x: direction < 0 ? 1000 : -1000,
      opacity: 0,
    };
  },
};

const swipeConfidenceThreshold = 10000;
const swipePower = (offset, velocity) => {
  return Math.abs(offset) * velocity;
};

export const SlideNotice = () => {
  const [[page, direction], setPage] = useState([0, 0]);
  const [isUserHover, setisUserHover] = useState(false);
  const imageIndex = wrap(0, images.length, page);

  const resetSec = () => {
    if (page === images.length) {
      setPage([0, 0]);
    }
  };

  useEffect(() => {
    resetSec();
    const interval = setInterval(() => {
      setPage([page + 1, 1]);
    }, 8000);

    return () => clearInterval(interval);
  }, [page]);

  const paginate = (newDirection) => {
    setPage([page + newDirection, newDirection]);
  };

  return (
    <NoticeWrapper>
      <AnimatePresence initial={false} custom={direction}>
        <Ad
          onClick={() => {
            alert("광고보기");
          }}
          onMouseEnter={() => {
            setisUserHover(true);
          }}
          onMouseLeave={() => {
            setisUserHover(false);
          }}
          key={page}
          src={images[imageIndex]}
          custom={direction}
          variants={variants}
          initial="enter"
          animate="center"
          exit="exit"
          transition={{
            x: { type: "spring", stiffness: 300, damping: 50 },
            opacity: { duration: 0.2 },
          }}
          drag="x"
          dragConstraints={{ left: 0, right: 0 }}
          dragElastic={1}
          onDragEnd={(e, { offset, velocity }) => {
            const swipe = swipePower(offset.x, velocity.x);
            if (swipe < -swipeConfidenceThreshold) {
              paginate(1);
            } else if (swipe > swipeConfidenceThreshold) {
              paginate(-1);
            }
          }}
        />
      </AnimatePresence>

      <LeftPagnigation onClick={() => paginate(-1)} whileTap={{ scale: 0.9 }}>
        좌
      </LeftPagnigation>
      <RightPagnigation onClick={() => paginate(1)} whileTap={{ scale: 0.9 }}>
        우
      </RightPagnigation>
    </NoticeWrapper>
  );
};
