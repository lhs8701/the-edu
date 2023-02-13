import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import { ADMIN_BAR_LIST } from "../static";

export function EventTable({ rows, cells, deleteFun, navigate }) {
  return (
    <TableContainer component={Paper}>
      <Table sx={{}} aria-label="simple table">
        <TableHead>
          <TableRow>
            {cells?.map((cell) => {
              return (
                <TableCell key={cell.id} align="left">
                  {cell?.name}
                </TableCell>
              );
            })}
          </TableRow>
        </TableHead>
        <TableBody>
          {rows?.map((row, idx) => (
            <TableRow
              key={row.id}
              onClick={() => {
                navigate(ADMIN_BAR_LIST.list[4].list[1].url + "/" + row.id);
              }}
            >
              <TableCell align="left">{row.id}</TableCell>
              <TableCell align="left">{row.title}</TableCell>
              <TableCell align="left">{row.startDate}</TableCell>
              <TableCell align="left">{row.endDate}</TableCell>
              <TableCell align="left">
                <button
                  onClick={() => {
                    deleteFun(row.id);
                  }}
                >
                  삭제
                </button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}

export function MyUploadCoursesTable({ rows, cells, deleteFun, navigate }) {}
