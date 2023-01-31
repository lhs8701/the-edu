import { FormControl, InputLabel, NativeSelect } from "@mui/material";
import { Box } from "@mui/system";
import { DashboardTitle } from "../../style/AdminCss";

export default function Revisecourses() {
  return (
    <>
      <DashboardTitle variant="h5">카테고리 수정</DashboardTitle>
      <Box sx={{ minWidth: 120 }}>
        <FormControl fullWidth>
          <InputLabel variant="standard" htmlFor="uncontrolled-native">
            Age
          </InputLabel>
          <NativeSelect
            defaultValue={30}
            inputProps={{
              name: "age",
              id: "uncontrolled-native",
            }}
          >
            <option value={10}>Ten</option>
            <option value={20}>Twenty</option>
            <option value={30}>Thirty</option>
          </NativeSelect>
        </FormControl>
      </Box>
    </>
  );
}
