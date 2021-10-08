r = "I am runoob site. welcome to runoob site.";
if ($bar =~ /run/i){
   print "第一次匹配\n";
}else{
   print "第一次不匹配\n";
}
 
$bar = "run";
if ($bar =~ /run/){
   print "第二次匹配\n";
}else{
   print "第二次不匹配\n";
}
