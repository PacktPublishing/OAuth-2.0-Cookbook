object session {
	def sum(f: Int => Int, a:Int, b:Int): Int = {
	  if (a > b) 0
	  else f(a) + sum(f, a + 1, b);
	}                                         //> sum: (f: Int => Int, a: Int, b: Int)Int
	
	def id (x: Int) = x                       //> id: (x: Int)Int
	def cube(x: Int) = x * x * x              //> cube: (x: Int)Int
	def fact(x: Int): Int = if (x == 0) 1 else fact(x - 1)
                                                  //> fact: (x: Int)Int
	
	def sumInts(a: Int, b: Int) = sum(id, a, b)
                                                  //> sumInts: (a: Int, b: Int)Int
	def sumCubes(a: Int, b: Int) = sum(cube, a, b)
                                                  //> sumCubes: (a: Int, b: Int)Int
	def sumFactorials(a: Int, b: Int) = sum(fact, a, b)
                                                  //> sumFactorials: (a: Int, b: Int)Int
	
	sumCubes(1, 4)                            //> res0: Int = 100
}