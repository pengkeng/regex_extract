<?php
$string = 'google 123, 456';
$pattern2 = '/(\w+) (\d+), (\d+)/i';
$replacement = 'runoob ${2},$3';

$subject = array('1', 'a', '2', 'b', '3', 'A', 'B', '4');
$pattern = array('/\d/', '/[a-z]/', '/[1a]/');
$replace = array('A:$0', 'B:$0', 'C:$0');
$replace1 = $string+ $pattern2 +"\w";
$replace2 = "abc"+ "\w[a-z]"+"aa";
$replace5 = "abc"+ "\w[a-z]" - "aa";
$replace3 = $replace2+ "\w[a-z]";
$replace4 = $pattern2;

echo "preg_filter 返回值：\n";
print_r(preg_filter('/a-z\w2/', $replace, $subject));
print_r(preg_filter($pattern, $replace, $subject));

echo "preg_replace 返回值：\n";
print_r(preg_replace($pattern, $replace, $subject));

$array = array(1, 2, 3.4, 53, 7.9);
// 返回所有包含浮点数的元素
$fl_array = preg_grep("/^(\d+)?\.\d+$/", $array);
print_r($fl_array);

function writeName()
{
    echo "Kai Jim Refsnes";

echo preg_replace($pattern2, $replacement, $string);
echo preg_replace($replace1, $replacement, $string);
echo preg_replace($replace2, $replacement, $string);
echo preg_replace($replace3 + $replace4, $replacement, $string);
}

?>