import { Typography } from "@mui/material";
import { useEffect, useLayoutEffect, useState } from "react";
import styled from "styled-components";
import { getFormResponse } from "../../api/adminApi";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";

export default function CreatorRequest() {
  const [rows, setRows] = useState([]);

  function createData(name, calories, fat, carbs, protein, categories) {
    return { name, calories, fat, carbs, protein, categories };
  }

  useEffect(() => {
    getFormResponse()
      .then(({ data }) => {
        console.log(data);
        const list = [];

        data?.items?.map((e) => {
          list.push(
            createData(
              e.answers[2].email,
              e.landed_at,
              e.answers[0].text,
              e.answers[1].phone_number,
              e.answers[3].text,
              e.answers[4].text
            )
          );
        });
        setRows(list);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  const BasicTable = () => {
    return (
      <TableContainer component={Paper}>
        <Table sx={{}} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell>이메일</TableCell>
              <TableCell align="right">날짜</TableCell>
              <TableCell align="right">성함</TableCell>
              <TableCell align="right">전화번호</TableCell>
              <TableCell align="right">개설 과목</TableCell>
              <TableCell align="right">기초 목차</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {rows?.map((row) => (
              <TableRow
                key={row.name}
                sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
              >
                <TableCell component="th" scope="row">
                  {row.name}
                </TableCell>
                <TableCell align="right">{row.calories}</TableCell>
                <TableCell align="right">{row.fat}</TableCell>
                <TableCell align="right">{row.carbs}</TableCell>
                <TableCell align="right">{row.protein}</TableCell>
                <TableCell align="right">{row.categories}</TableCell>
                <TableCell align="right">
                  <button>등록</button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    );
  };

  return (
    <>
      <Typography variant="h4" mb={5}>
        새 강좌 신청 목록
      </Typography>
      <BasicTable />
    </>
  );
}
