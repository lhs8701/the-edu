import { useEffect, useState } from "react";
import { motion, AnimatePresence, wrap } from "framer-motion";
import styled from "styled-components";
import { PROCESS_MAIN_URL, STATIC_URL } from "../static";
import { useNavigate } from "react-router";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import { Icon } from "@mui/material";

const NoticeWrapper = styled(motion.div)`
  width: 100%;
  height: 300px;
  display: flex;
  position: relative;
  border-radius: var(--size-border-radius);
  overflow: hidden;
  margin-bottom: 50px;
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

export const SlideNotice = ({ eventList }) => {
  const [[page, direction], setPage] = useState([0, 0]);
  const [isUserHover, setisUserHover] = useState(false);
  const imageIndex = wrap(0, eventList.length, page);
  const navigate = useNavigate();
  const resetSec = () => {
    if (page === eventList.length) {
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
            navigate(`${PROCESS_MAIN_URL.EVENT}/${eventList[imageIndex].id}`);
          }}
          onMouseEnter={() => {
            setisUserHover(true);
          }}
          onMouseLeave={() => {
            setisUserHover(false);
          }}
          key={page}
          src={STATIC_URL + eventList[imageIndex].bannerImage.originalFilePath}
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
        <Icon
          sx={{ width: "30px", height: "30px" }}
          component={ArrowBackIosNewIcon}
        />
      </LeftPagnigation>
      <RightPagnigation onClick={() => paginate(1)} whileTap={{ scale: 0.9 }}>
        <Icon
          sx={{ width: "30px", height: "30px" }}
          component={ArrowForwardIosIcon}
        />
      </RightPagnigation>
    </NoticeWrapper>
  );
};
