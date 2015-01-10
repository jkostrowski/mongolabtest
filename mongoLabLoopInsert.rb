require 'rubygems'
require 'mongo'
require 'pp'
require 'benchmark'

include Mongo

def init
	@db = MongoClient.from_uri( "mongodb://user1:sws11wed@ds049170.mongolab.com:49170/jko" ).db( "jko" )      
	#@db = MongoClient.from_uri( "mongodb://localhost:27017/jko" ).db( "jko" )   

	@db.drop_collection('foo')
	@cl = @db.create_collection('foo')

	1.times { |i| @cl.insert( 'a' => i+1, '_id' => i+1) }
	
	#@cl.find().each { |row| puts row }
end

def insert_loop( a )
    while (1) do
		begin
			i = @cl.find( {}, { :sort => { '_id' => -1 }, :limit =>  1 } ).map { |x| x['_id'] }.reduce( 1, :+ )
			@cl.insert( { '_id' => i } )
			break
		rescue Exception => e  
			next if e.result[ 'code' ] 			     == 11000  # E11000 duplicate  2.4  
			#next if e.result[ 'writeErrors' ][0]['code'] == 11000  # E11000 duplicate  2.6
			abort e.message   
		end
    end
end

def insert_plain( a )
	begin
		@cl.insert( { 'id' => a }  )
	rescue Exception => e  
		abort e.message   
	end
end


init()
Benchmark.bm do |x|
	x.report { 100.times { |i| insert_loop( i+2 ) } }     # lab=15s/100  local=2.5s/100  #
	x.report { 100.times { |i| insert_plain( i+102 ) } }  # lab=6s/100  local=1.2s/100  #
end