import { Typography } from "@mui/material";
import { useState } from "react";
import { BASE_URL } from "../../static";
import axios from "axios";

export default function CoursesComment() {
  const [file, setFile] = useState("");
  const [file1, setFile1] = useState("");

  const upload = (e) => {
    e.preventDefault();
    const formData = new FormData();
    formData.append("multipartFiles", file);
    formData.append("multipartFiles", file1);
    axios
      .post(`${BASE_URL}/images/multiple`, formData, {
        headers: {
          "X-AUTH-TOKEN":
            "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQG5hdmVyLmNvbSIsImFjY291bnQiOiJ1c2VyQG5hdmVyLmNvbSIsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSIiwiaWF0IjoxNjczMzM2MzgxLCJleHAiOjE3MDQ4NzIzODF9.6d5Ai_OEyCUzTclcyj70WWrqK6gfZOYPDFBtdJhfP8M",
        },
      })
      .then(({ data }) => {
        // make(data.mediumFilePath);
      })

      .catch((err) => {
        alert(err);
      });
  };

  const make = (mediumFilePath) => {
    axios
      .post(
        `${BASE_URL}/courses`,
        {
          title: "스프링 핵심 원리 - 상급편",
          description:
            "스프링 입문자가 예제를 만들어가면서 스프링의 핵심 원리를 이해할 수 있습니다.",
          category: "백엔드",
          price: 143000,
          imageDto: {
            thumbnailImage: mediumFilePath,
            descriptionImages: [mediumFilePath],
          },
        },
        {
          headers: {
            "Content-Type": "application/json",
            "X-AUTH-TOKEN":
              "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQG5hdmVyLmNvbSIsImFjY291bnQiOiJ1c2VyQG5hdmVyLmNvbSIsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSIiwiaWF0IjoxNjczMzM2MzgxLCJleHAiOjE3MDQ4NzIzODF9.6d5Ai_OEyCUzTclcyj70WWrqK6gfZOYPDFBtdJhfP8M",
          },
        }
      )
      .then(({ data }) => {
        console.log(data);
      })

      .catch((err) => {
        alert(err);
      });
  };

  return (
    <div>
      <Typography variant="h4" mb={1}>
        강좌 댓글 관리
      </Typography>
      <input
        type="file"
        id="summary_image"
        accept=".jpg, .jpeg, .png"
        placeholder="Attach File"
        onChange={(e) => {
          setFile(e.target.files[0]);
        }}
      />
      <input
        type="file"
        id="summary_image"
        accept=".jpg, .jpeg, .png"
        placeholder="Attach File"
        onChange={(e) => {
          setFile1(e.target.files[0]);
        }}
      />
      <button onClick={upload}>업로드</button>
    </div>
  );
}
